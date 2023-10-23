package kz.arabro.telephony.util.migration;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import kz.arabro.telephony.adapter.repository.dao.CustomerDao;
import kz.arabro.telephony.util.stub.CustomerMongoDbModelStub;

@ChangeLog
public class MongoDBChangeLog {

    private static final int COUNT_CUSTOMER = 10;

    @ChangeSet(order = "2", id = "init-mongo", author = "arabro")
    public void changeSet(CustomerDao customerDao) {
        var customerMongoDBModels = CustomerMongoDbModelStub.getCustomerMongoDBModels(COUNT_CUSTOMER);
        customerDao.saveAll(customerMongoDBModels);
    }
}
