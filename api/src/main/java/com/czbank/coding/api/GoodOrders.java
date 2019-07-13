package com.czbank.coding.api;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "GOOD_ORDERS")
public class GoodOrders implements Serializable,Cloneable{
    /** 订单物品ID */
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id ;
    /** 订单ID */
    private Integer ordersId ;
    /** 物品ID */
    private Integer goodId ;
    /** 物品数量 */
    private Integer number ;
    /** 乐观锁 */
    private Integer revision ;
    /** 创建人 */
    private String createdBy ;
    /** 创建时间 */
    private LocalDateTime createdTime ;
    /** 更新人 */
    private String updatedBy ;
    /** 更新时间 */
    private LocalDateTime updatedTime ;
}