package com.labella.lapag.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    public enum Values {

        ADMIN(1L),
        BASIC(2L);

        long rotaId;

        Values(long rotaId) {
            this.rotaId = rotaId;
        }

        public long getRotaId() {
            return rotaId;
        }

    }
}
