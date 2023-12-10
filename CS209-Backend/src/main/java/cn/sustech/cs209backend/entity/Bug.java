package cn.sustech.cs209backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "bugs")
public class Bug {

    @Id
    @Column(name = "bug_name")
    private String bugName;

}
