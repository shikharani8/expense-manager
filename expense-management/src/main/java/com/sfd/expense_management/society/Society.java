package com.sfd.expense_management.society;

import com.sfd.expense_management.street.Street;
import com.sfd.expense_management.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tb_societies")
@Data
public class Society {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne
    private User superAdmin;
    @OneToMany
    private Set<Street> streets = new HashSet<>();

    public void setStreets(Set<Street> streets){
        this.streets.addAll(streets);
    }
}
