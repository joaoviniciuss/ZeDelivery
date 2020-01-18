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
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String tradingName;

    @NotEmpty(message = "Owner name can't be empty")
    @NotBlank(message = "Owner can't be blank")
    @NotNull
    private String ownerName;

    @NotEmpty(message = "Document can't be empty")
    @NotBlank(message = "Document can't be blank")
    @NotNull
    @Column(unique = true)
    private String document;

    @NotNull
    private MultiPolygon coverageArea;

    @NotNull
    private Point address;

    public boolean containPoint(Point point){
        return coverageArea.contains(point);
    }

    public double getDistanceToPoint(Point point){
        return address.distance(point);
    }

}