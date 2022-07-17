package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.CarDTO;
import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.dto.ReservationDTO;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.entity.Reservation;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.repo.ReservationRepo;
import lk.ijse.spring.service.DriverService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepo repo;

    @Autowired
    private ReservationRepo re_repo;

    @Autowired
    private ModelMapper mapper;

    @Override
    public void saveDriver(DriverDTO dto) {
        if (!repo.existsById(dto.getNic())) {
            repo.save(mapper.map(dto, Driver.class));
        } else {
            throw new RuntimeException("Driver Already Exist..!");
        }

    }

    @Override
    public DriverDTO getDriverByID(String driverNIC) {
        if (repo.existsById(driverNIC)) {
            return mapper.map(repo.findById(driverNIC).get(),DriverDTO.class);
        } else {
            throw new RuntimeException("No such a Driver..!");
        }
    }

    @Override
    public void updateDriver(DriverDTO dto) {
        if (repo.existsById(dto.getNic())) {
            repo.save(mapper.map(dto, Driver.class));
        } else {
            throw new RuntimeException("No such a Driver, Please check driver NIC");
        }
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return mapper.map(repo.findAll(), new TypeToken<List<DriverDTO>>() {
        }.getType());
    }

    @Override
    public DriverDTO getRandomDriver(String start_date, String end_date) {
        return mapper.map(repo.getRandomDriver(start_date,end_date),DriverDTO.class);
    }

    @Override
    public List<Object> DriverScheduleByDate(String driver_nic, String start_date, String end_date) {
        return mapper.map(repo.DriverScheduleByDate(driver_nic,start_date,end_date), new TypeToken<List<Object>>() {
        }.getType());
    }

    @Override
    public List<DriverDTO> todayAvailableDrivers() {
        return mapper.map(repo.todayAvailableDrivers(), new TypeToken<List<DriverDTO>>() {
        }.getType());
    }

    @Override
    public List<DriverDTO> todayUnavailableDrivers() {
        return mapper.map(repo.todayUnavailableDrivers(), new TypeToken<List<DriverDTO>>() {
        }.getType());
    }

    @Override
    public ReservationDTO changeDriverInReservation(String reservation_id, String driver_nic) {
        if (re_repo.existsById(reservation_id)) {
            Reservation reservation = re_repo.findById(reservation_id).get();
            reservation.setDriver(repo.findById(driver_nic).get());
            return mapper.map(reservation,ReservationDTO.class);
        } else {
            throw new RuntimeException("No such a Reservation to update...!");
        }
    }
}
