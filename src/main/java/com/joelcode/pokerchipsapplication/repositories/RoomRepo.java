package com.joelcode.pokerchipsapplication.repositories;

import com.joelcode.pokerchipsapplication.entities.Room;
import com.joelcode.pokerchipsapplication.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepo extends JpaRepository<Room, UUID> {

    Optional<Room> findByRoomName(String roomName);

    List<Room> findByStatus(Room.roomStatus status);

    List<Room> findByHost(User host);


    // Find rooms that aren't full (have space for more players)
    @Query("SELECT r FROM Room r " +
            "WHERE r.status = :status AND " +
            "SIZE(r.roomPlayers) < r.maxPlayers")
    List<Room> findAvailableRooms(@Param("status") Room.roomStatus status);

    // Find active rooms with minimum number of players
    @Query("SELECT r FROM Room r " +
            "WHERE r.status = 'ACTIVE' AND " +
            "SIZE(r.roomPlayers) >= :minPlayers " +
            "ORDER BY SIZE(r.roomPlayers) DESC")
    List<Room> findActiveRoomsWithMinPlayers(@Param("minPlayers") int minPlayers);
    // Count total active rooms
    @Query("SELECT COUNT(r) FROM Room r WHERE r.status = 'ACTIVE'")
    Long countActiveRooms();


    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.status = :newStatus WHERE r.id = :roomId")
    int updateRoomStatus(@Param("roomId") UUID roomId, @Param("newStatus") Room.roomStatus newStatus);

    @Modifying
    @Transactional
    @Query("UPDATE Room r SET r.endedAt = :endTime WHERE r.status = :status")
    int endAllRoomsWithStatus(@Param("status") Room.roomStatus status, @Param("endTime") LocalDateTime endTime);
}
