package com.sfd.expense_management.role;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Entity
@Table(name="tb_roles")
@Data
public class Role {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

 @NotBlank
 @NotEmpty
 @NotNull
  private String name;

}
