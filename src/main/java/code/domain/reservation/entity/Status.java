package code.domain.reservation.entity;

import lombok.Getter;

@Getter
public enum Status {
    PENDING,
    CONFIRMED,
    CANCELLED,
    COMPLETED
}
