package kr.co.mgv.store.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Alias("OrderProduct")
public class OrderProduct {

    private int productNo;
    private boolean isPackage;
    private String name;
    private int amount;
    private int unitPrice;
    private Date orderDate;
    private String imagePath;
}
