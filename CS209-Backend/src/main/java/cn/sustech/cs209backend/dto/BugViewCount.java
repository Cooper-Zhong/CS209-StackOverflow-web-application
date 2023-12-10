package cn.sustech.cs209backend.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedNativeQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BugViewCount {


    private String bugName;

    private int average_view_count;
}
