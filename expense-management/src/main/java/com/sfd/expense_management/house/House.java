package com.sfd.expense_management.house;

import com.sfd.expense_management.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name="tb_houses")
@Data
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String address;
    @OneToOne
    private User owner;
    private Long streetId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Objects.equals(id, house.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
