package com.FlightCrew.repository;

import com.FlightCrew.model.SyncControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SyncControlRepository extends JpaRepository<SyncControl, Long> {
    Optional<SyncControl> findByDeviceId(String deviceId);
    Optional<SyncControl> findByDeviceIdAndSyncToken(String deviceId, String syncToken);
}