package mybatis.dao;

import mybatis.entity.Goods;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface GoodsDAO {
    @Select("select * from t_goods where current_price between #{min} and #{max}order by current_price limit 0,#{limit}")
    public List<Goods> selectByPriceRang(@Param("min") Float min, @Param("max") Float max, @Param("limit") Integer limit);

    @Insert("insert into t_goods(title, sub_title, original_cost, current_price, discount, is_free_delivery, category_id) values (#{title}, #{subTitle}, #{originalCost}, #{currentPrice}, #{discount}, #{isFreeDelivery}, #{categoryId})")
    @SelectKey(statement = "select last_insert_id()", before = false, keyProperty = "goodsId", resultType = Integer.class)
    public int insert(Goods goods);

    @Select("select * from t_goods")
    @Results({
            @Result(column = "goods_id", property = "goodsId", id = true),
            @Result(column = "title", property = "title"),
            @Result(column = "sub_title", property = "subTitle"),
            @Result(column = "original_cost", property = "originalCost"),
            @Result(column = "current_price", property = "currentPrice"),
            @Result(column = "discount", property = "discount"),
            @Result(column = "is_free_delivery", property = "isFreeDelivery"),
            @Result(column = "category_id", property = "categoryId")
    })
    public List<Goods> selectAll();
}
