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
@TableName(value = "GOOD")
public class Good implements Serializable,Cloneable{

    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id ;
    /** 物品名 */
    private String goodName ;
    /** 积分价格 */
    private Integer scorePrice ;
    /** 物品价格 */
    private Double price ;
    /** 物品可用积分 */
    private Integer minus ;
    /** 物品折后价格 */
    private Double priceMinus ;
    /** 物品描述短 */
    private String descriptionShort ;
    /** 物品描述长 */
    private String descriptionLong ;
    /** 物品图片名 */
    private String picName ;
    /** 供应商 */
    private String supplier ;
    /** 库存 */
    private Integer store ;
    /** 物品大分类 */
    private String bigClassify ;
    /** 物品小分类 */
    private String smallClassify ;
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