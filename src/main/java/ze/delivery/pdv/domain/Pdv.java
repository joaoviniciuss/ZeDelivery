package ze.delivery.pdv.domain;

import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import lombok.*;
import ze.delivery.commons.base.application.DataTransferObject;
import ze.delivery.commons.base.domain.EntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "pdv")
public class Pdv implements EntityBase, DataTransferObject {

    @Id
    @Setter
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Trading name can't be empty")
    @NotBlank(message = "Trading can't be blank")
    private String tradingName;

    @NotEmpty(message = "Owner name can't be empty")
    @NotBlank(message = "Owner can't be blank")
    private String ownerName;

    @NotEmpty(message = "Document can't be empty")
    @NotBlank(message = "Document can't be blank")
    private String document;

    //@Column(name = "coverageArea",columnDefinition="Polygon[]")
    private MultiPolygon coverageArea;

    //@Column(name = "address",columnDefinition="Point")
    private Point address;

}
