package com.green.namu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String setName;

    private String menuNames;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
