package com.example.backendassignment.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Table(name = "data")
@Entity
@Getter
@Setter
@ToString
public class Data {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source")
    private String source;

    @Column(name = "code_list_code")
    private String codeListCode;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "display_value")
    private String displayValue;

    @Column(name = "long_description")
    private String longDescription;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "sorting_priority")
    private Integer sortingPriority;

}
