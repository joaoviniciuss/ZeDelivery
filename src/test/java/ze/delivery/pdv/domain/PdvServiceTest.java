package ze.delivery.pdv.domain;

import com.vividsolutions.jts.geom.*;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ze.delivery.PostgresSQLTestsConfiguration;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
        PostgresSQLTestsConfiguration.class,
        PdvService.class,
})
public class PdvServiceTest {

    @Autowired
    PdvService service;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void shouldRunAllRestVerbsCorrectly() throws IOException {
        final Pdv pdvToSave = createPdv(new Coordinate(-46.47441029548645, -23.544258235698877), "1432132123891/0001");
        final ResponseEntity<Pdv> createdResult = service.save(pdvToSave);
        final Pdv createdPdv = createdResult.getBody();
        Assert.assertThat(createdPdv, Matchers.notNullValue());

        final List<Pdv> findAllResult = service.findAll();
        Assert.assertThat(findAllResult, Matchers.hasSize(1));

        final Long id = createdPdv.getId();
        final ResponseEntity findByIdResult = service.findById(id);
        final Object pdvById = findByIdResult.getBody();
        Assert.assertThat(pdvById, Matchers.notNullValue());

        final ResponseEntity findByPoint = service.findByPoint("-46.47370219230652", "-23.54631387667149");
        final Object pdvByPoint = findByPoint.getBody();
        Assert.assertThat(pdvByPoint, Matchers.notNullValue());

        doNotSavePdvWithSomeDocument();

    }

    @Test
    public void doNotSavePdvWithAddressOutsideCoverageArea() throws IOException {

        final Pdv pdvToNonSave = createPdv(new Coordinate(-46.42513275146484, -23.563357737930875), "1432132123891/0004");
        final ResponseEntity<Pdv> noCreatedResult = service.save(pdvToNonSave);
        Assert.assertThat(noCreatedResult.getStatusCodeValue(), Matchers.equalTo(400));
    }


    public void doNotSavePdvWithSomeDocument() throws IOException {

        try {
            final Pdv pdvToNonSave = createPdv(new Coordinate(-46.47441029548645, -23.544258235698877), "1432132123891/0001");
        } catch (Exception e) {
            exceptionRule.expect(DataIntegrityViolationException.class);
        }


    }

    private Pdv createPdv(Coordinate address, String document) throws IOException {
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(address);

        Coordinate[] coords = new Coordinate[]{
                new Coordinate(-46.475815773010254, -23.543854972215986),
                new Coordinate(-46.47586941719055, -23.546776145574036),
                new Coordinate(-46.47333741188049, -23.5472285773942),
                new Coordinate(-46.472575664520264, -23.546127001547458),
                new Coordinate(-46.47233963012695, -23.545920455048698),
                new Coordinate(-46.47209286689758, -23.545812263896046),
                new Coordinate(-46.471813917160034, -23.54578275720259),
                new Coordinate(-46.471771001815796, -23.545704072654374),
                new Coordinate(-46.47147059440613, -23.54574341493437),
                new Coordinate(-46.471813917160034, -23.54411070042177),
                new Coordinate(-46.47239327430725, -23.544071357653248),
                new Coordinate(-46.47287607192993, -23.543884479342033),
                new Coordinate(-46.475815773010254, -23.543854972215986)
        };

        Polygon[] polygons = new Polygon[]{
                gf.createPolygon(gf.createLinearRing(coords), null)
        };

        final MultiPolygon multiPolygon = gf.createMultiPolygon(polygons);

        return Pdv.builder()
                .tradingName("Fiel Zone")
                .document("1432132123891/0001")
                .ownerName("30 milhões de loucos")
                .address(point)
                .coverageArea(multiPolygon)
                .build();
    }

}
