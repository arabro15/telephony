package kz.arabro.telephony.util.stub;

import kz.arabro.telephony.adapter.repository.model.CustomerMongoDBModel;
import kz.arabro.telephony.domain.entity.CustomerID;
import kz.arabro.telephony.util.generator.NumberGenerator;
import kz.arabro.telephony.util.generator.StringGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public final class CustomerMongoDbModelStub {

    public static CustomerMongoDBModel getCustomerMongoDBModel() {
        var customerMongoDBModel = new CustomerMongoDBModel();
        customerMongoDBModel.setCustomerID(CustomerID.newID().getValue());
        customerMongoDBModel.setName(StringGenerator.getRandomString());
        customerMongoDBModel.setYearOfBirth(String.valueOf(NumberGenerator.getRandomNumber(1940, 2023)));
        customerMongoDBModel.setFirstPhone(String.valueOf(NumberGenerator.getRandomNumber(87_700_000_000L, 87_777_999_999L)));
        customerMongoDBModel.setSecondPhone(String.valueOf(NumberGenerator.getRandomNumber(87_700_000_000L, 87_777_999_999L)));
        customerMongoDBModel.setCreatedAt(String.valueOf(Instant.now()));

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
