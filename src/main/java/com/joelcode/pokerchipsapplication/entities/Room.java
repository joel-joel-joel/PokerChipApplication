package com.joelcode.pokerchipsapplication.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@Entity
@Table(name = "room")
public class Room {

    public Room(String roomName, User host, roomStatus status, int maxPlayers, int staringChips, ArrayList<RoomPlayer> roomPlayers) {
        this.roomName = roomName;
        this.host = host;
        this.status = status;
        this.maxPlayers = maxPlayers;
        this.staringChips = staringChips;
        this.roomPlayers = roomPlayers;
    }

    public Room() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host")
    private User host;

    @Enumerated(EnumType.STRING)
    private roomStatus status = roomStatus.WAITING;

    @Column(name = "max_players")
    private int maxPlayers = 10;

    @Column(name = "staring_chips")
    private int staringChips = 1000;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime endedAt;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private ArrayList<RoomPlayer> roomPlayers = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public roomStatus getStatus() {
        return status;
    }

    public void setStatus(roomStatus status) {
        this.status = status;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getStaringChips() {
        return staringChips;
    }

    public void setStaringChips(int staringChips) {
        this.staringChips = staringChips;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public ArrayList<RoomPlayer> getRoomPlayers() {
        return roomPlayers;
    }

    public void setRoomPlayers(ArrayList<RoomPlayer> roomPlayers) {
        this.roomPlayers = roomPlayers;
    }
}

enum roomStatus {
    WAITING, ACTIVE, END
}
