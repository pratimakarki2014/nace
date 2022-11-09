package com.demo.nace.model.entity;

import lombok.Data;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;

@Entity
@Table(name = "nace_detail_tbl")
@Data
public class NACEDetail {

    @Column(name = "nace_order")
    @Id
    private String naceOrder;
    @Column
    private String level;
    @Column
    private String code;
    @Column
    private String parent;
    @Column
    @Lob
    private String description;
    @Column(name = "item_includes")
    @Lob
    private String itemIncludes;
    @Column(name = "item_also_includes")
    @Lob
    private String itemAlsoIncludes;
    @Column
    @Lob
    private String rulings;
    @Column(name = "item_excludes")
    @Lob
    private String itemExcludes;
    @Column(name = "ref_to_isic_rev4")
    private String referenceToISICRev4;

}
