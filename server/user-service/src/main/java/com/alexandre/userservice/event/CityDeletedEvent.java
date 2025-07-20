package com.alexandre.userservice.event;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDeletedEvent {
    private UUID cityId;
}
