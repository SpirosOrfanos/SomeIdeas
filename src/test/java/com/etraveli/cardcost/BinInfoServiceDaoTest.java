package com.etraveli.cardcost;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import com.etraveli.cardcost.domain.dbo.BinInfo;
import com.etraveli.cardcost.exceptions.ActionNotSupportedException;
import com.etraveli.cardcost.service.BinInfoServiceDao;
import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class BinInfoServiceDaoTest {

  @Autowired
  private BinInfoServiceDao binInfoServiceDao;
  
  @Test
  void test() {
    
    BinInfo binInfo = BinInfo.builder().country("gr").id("445533").build();
    assertThrows(ActionNotSupportedException.class, () -> binInfoServiceDao.delete(""));
    assertThrows(ActionNotSupportedException.class, () -> binInfoServiceDao.getPaginated(PageRequest.of(0, 1)));
    assertThrows(ActionNotSupportedException.class, () -> binInfoServiceDao.getAll());
    assertThrows(ActionNotSupportedException.class, () -> binInfoServiceDao.update(binInfo));
    
    BinInfo res = binInfoServiceDao.save(binInfo);
    assertEquals("gr", res.getCountry());
    assertEquals("gr", binInfoServiceDao.get("445533").get().getCountry());
    
  }
}
