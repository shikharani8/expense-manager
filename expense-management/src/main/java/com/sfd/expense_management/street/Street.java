package com.sfd.expense_management.street;

import com.sfd.expense_management.house.House;
import com.sfd.expense_management.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="tb_streets")
@Data
public class Street {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private User admin;
    @OneToMany
    private Set<House> houses= new HashSet<>();

    public void setHouses(Set<House> houses){ this.houses.addAll(houses);}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return Objects.equals(id, street.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
