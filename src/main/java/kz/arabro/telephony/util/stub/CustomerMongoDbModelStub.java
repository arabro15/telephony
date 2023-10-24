package kz.arabro.telephony.util.stub;

import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class CustomerMongoDbModelStub {

    public static CustomerMongoDBModel getCustomerMongoDBModel() {
        var customerMongoDBModel = new CustomerMongoDBModel();
        customerMongoDBModel.setCustomerID(CustomerID.newID().getValue());
        customerMongoDBModel.setName(StringGenerator.getRandomString());
        customerMongoDBModel.setYearOfBirth(String.valueOf(NumberGenerator.getRandomNumber(1920, 2023)));
        customerMongoDBModel.setFirstPhone(String.valueOf(NumberGenerator.getRandomNumber(8_700_000_0000L, 8_778_999_9999L)));
        customerMongoDBModel.setSecondPhone(String.valueOf(NumberGenerator.getRandomNumber(8_700_000_0000L, 8_778_999_9999L)));
        customerMongoDBModel.setCreatedAt(Timestamp.from(Instant.now()).toString());

        return customerMongoDBModel;
    }

    public static List<CustomerMongoDBModel> getCustomerMongoDBModels(int count ) {
        var customerMongoDbModels = new ArrayList<CustomerMongoDBModel>();

        for (int i = 0; i < count; i++) {
            customerMongoDbModels.add(getCustomerMongoDBModel());
        }

        return customerMongoDbModels;
    }

    private CustomerMongoDbModelStub() {}
}
