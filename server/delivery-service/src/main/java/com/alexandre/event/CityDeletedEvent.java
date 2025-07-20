package com.alexandre.event;

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
