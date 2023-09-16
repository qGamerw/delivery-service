package ru.sber.delivery.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "shifts")
public class Shift {
    @Id
    @Column(name = "id_shift")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "begin_shift")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @NotBlank
    private LocalDateTime beginShift;

    @Column(name = "end_shift")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime endShift;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
