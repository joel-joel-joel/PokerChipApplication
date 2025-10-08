package com.joelcode.pokerchipsapplication.service;


import com.joelcode.pokerchipsapplication.entities.Room;
import com.joelcode.pokerchipsapplication.repositories.RoomPlayerRepo;
import com.joelcode.pokerchipsapplication.repositories.RoomRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    @Autowired
    private RoomPlayerRepo roomPlayerRepo;

    public Room createNewRoom() {
        return null;
    }



}
