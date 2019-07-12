package api;

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
@TableName(value = "ORDERS")
public class Orders implements Serializable,Cloneable{

    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id ;
    /** 账号ID */
    private Integer accountId ;
    /** 地址 */
    private String adrress ;
    /** 手机号 */
    private String phone ;
    /** 备注 */
    private String note ;
    /** 状态 */
    private String status ;
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