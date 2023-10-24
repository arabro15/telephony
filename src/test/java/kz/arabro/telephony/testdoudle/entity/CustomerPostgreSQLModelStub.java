package kz.arabro.telephony.testdoudle.entity;

import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.adapter.repository.model.CustomerPostgreSQLModel;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class CustomerPostgreSQLModelStub {

    public static CustomerPostgreSQLModel getCustomerPostgreSQLModel() {
        var customerPostgreSQLModel = new CustomerPostgreSQLModel();
        customerPostgreSQLModel.setCustomerID(CustomerID.newID().getValue());
        customerPostgreSQLModel.setName(StringGenerator.getRandomString());
        customerPostgreSQLModel.setYearOfBirth(String.valueOf(NumberGenerator.getRandomNumber(1920, 2023)));
        customerPostgreSQLModel.setFirstPhone(String.valueOf(NumberGenerator.getRandomNumber(8_700_000_0000L, 8_778_999_9999L)));
        customerPostgreSQLModel.setSecondPhone(String.valueOf(NumberGenerator.getRandomNumber(8_700_000_0000L, 8_778_999_9999L)));
        customerPostgreSQLModel.setCreatedAt(Timestamp.from(Instant.now()));

        return customerPostgreSQLModel;
    }

    public static List<CustomerPostgreSQLModel> getCustomerPostgreSQLModels(int count ) {
        var customerPostgreSQLModels = new ArrayList<CustomerPostgreSQLModel>();

        for (int i = 0; i < count; i++) {
            customerPostgreSQLModels.add(getCustomerPostgreSQLModel());
        }

        return customerPostgreSQLModels;
    }
}
