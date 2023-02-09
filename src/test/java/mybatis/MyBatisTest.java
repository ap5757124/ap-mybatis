package mybatis;

import mybatis.entity.Goods;
import mybatis.utils.MyBatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyBatisTest {

    /*
     * 查询t_goods 的10条数据
     * */
    @Test
    public void testGoodsSelectAll() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            //调用goods.xml 命名空间为goods 下面的selectAll 语句
            List<Goods> list = session.selectList("goods.selectAll");
            for (Goods g : list) {
                System.out.println(g.getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /*
     * 通过id查询t_goods
     * */
    @Test
    public void testGoodsSelectById() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 1602);
            System.out.println(goods.getTitle());
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /*
     * 通过价格区间查询t_goods
     * */
    @Test
    public void testGoodsSelectByPriceRang() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Map param = new HashMap();
            param.put("min", 100);
            param.put("max", 500);
            param.put("limit", 10);
            //保证是全局唯一的就可以不加 goods 命名空间
            List<Goods> list = session.selectList("selectByPriceRang", param);
            for (Goods g : list) {
                System.out.println(g.getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }

    }
}
