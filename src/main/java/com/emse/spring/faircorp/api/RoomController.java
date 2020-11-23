package com.emse.spring.faircorp.api;


import com.emse.spring.faircorp.dao.HeaterDao;
import com.emse.spring.faircorp.dao.RoomDao;
import com.emse.spring.faircorp.dao.WindowDao;
import com.emse.spring.faircorp.dto.RoomDto;
import com.emse.spring.faircorp.model.HeaterStatus;
import com.emse.spring.faircorp.model.Room;
import com.emse.spring.faircorp.model.WindowStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rooms")
@Transactional
public class RoomController {

    private final RoomDao roomDao;
    private final HeaterDao heaterDao;
    private final WindowDao windowDao;

    public RoomController(RoomDao roomDao, HeaterDao heaterDao, WindowDao windowDao) {
        this.roomDao = roomDao;
        this.heaterDao = heaterDao;
        this.windowDao = windowDao;
    }

    @GetMapping(path = "")
    public List<RoomDto> findAll() {
        return roomDao.findAll().stream().map(RoomDto::new).collect(Collectors.toList());
    }

    @GetMapping(path = "/{id}")
    public RoomDto findById(@PathVariable Long id) {
        return roomDao.findById(id).map(RoomDto::new).orElse(null);
    }

    @PostMapping
    public RoomDto create(@RequestBody RoomDto dto) {
        Room room;
        if (dto.getId() == null) {
            room = roomDao.save(new Room(dto.getFloor(), dto.getName()));
        }
        else {
            room = roomDao.getOne(dto.getId());
        }
        room.setCurrentTemperature(dto.getCurrentTemperature());
        room.setTargetTemperature(dto.getTargetTemperature());
        room.setHeaters(dto.getHeaterIds().stream().map(heaterDao::getOne).collect(Collectors.toSet()));
        room.setWindows(dto.getWindowIds().stream().map(windowDao::getOne).collect(Collectors.toSet()));
        return new RoomDto(room);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable Long id) {
        Room room = roomDao.getOne(id);
        heaterDao.deleteInBatch(room.getHeaters());
        windowDao.deleteInBatch(room.getWindows());
        roomDao.deleteById(id);
    }

    @PutMapping(path = "/{id}/switchWindows")
    public void switchWindows(@PathVariable Long id) {
        Room room = roomDao.getOne(id);
        room.setWindows(room.getWindows().stream().peek(window -> window.setWindowStatus(
                window.getWindowStatus().equals(WindowStatus.OPEN)?WindowStatus.CLOSED:WindowStatus.OPEN
        )).collect(Collectors.toSet()));
    }

    @PutMapping(path = "/{id}/switchHeaters")
    public void switchHeaters(@PathVariable Long id) {
        Room room = roomDao.getOne(id);
        room.setHeaters(room.getHeaters().stream().peek(heater -> heater.setHeaterStatus(
                heater.getHeaterStatus().equals(HeaterStatus.ON)? HeaterStatus.OFF:HeaterStatus.ON
        )).collect(Collectors.toSet()));
    }
}
