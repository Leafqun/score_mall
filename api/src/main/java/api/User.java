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
@TableName(value = "USER")
public class User implements Serializable,Cloneable{
    /** 账号ID */
    @TableId(value = "id" , type = IdType.AUTO)
    private Integer id ;
    /** 昵称 */
    private String nickname ;
    /** 手机号 */
    private String phone ;
    /** 银行卡号 */
    private String bankaccount ;
    /** 账号密码 */
    private String password ;
    /** 账号类型 */
    private String type ;
    /** 账号状态 */
    private String status ;
    /** 积分 */
    private String score ;
    /** 姓名 */
    private String name ;
    /** 地址 */
    private String address ;
    /** 邮箱 */
    private String mail ;
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
