package com.joelcode.pokerchipsapplication.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class TransferChipsRequest {
    @NotNull (message = "To player ID is required")
    private UUID toPlayerId;

    @NotNull (message = "From player ID is required")
    private UUID fromPlayerId;

    @NotNull (message = "Amount is required")
    @Min (value = 1, message = "Amount must be more than 1")
    private Integer amount;

    @NotNull (message = "Description is required")
    private String description;

    public TransferChipsRequest() {}

    public TransferChipsRequest(UUID toPlayerId, UUID fromPlayerId, Integer amount, String description) {
        this.toPlayerId = toPlayerId;
        this.fromPlayerId = fromPlayerId;
        this.amount = amount;

    }

    public UUID getToPlayerId() {return toPlayerId;}
    public void setToPlayerId(UUID toPlayerId) {this.toPlayerId = toPlayerId;}

    public UUID getFromPlayerId() {return fromPlayerId;}
    public void setFromPlayerId(UUID fromPlayerId) {this.fromPlayerId = fromPlayerId;}

}
