package com.XCLONE.Budget_tracker.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)  // use LAZY for performance
    @JoinColumn(name = "user_id")
    private Users user;
}
