package mybatis;

import mybatis.dto.GoodsDTO;
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
     * select查询语句执行
     * @throws Exception
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
     * 传递单个SQL参数
     * @throws Exception
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
     * 传递多个SQL参数
     * @throws Exception
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
                System.out.println(g.getTitle() + ":" + g.getCurrentPrice());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * 利用Map接收关联查询结果
     * @throws Exception
     */
    @Test
    public void testSelectGoodsMap() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<Map> list = session.selectList("goods.selectGoodsMap");
            for (Map m:list) {
                System.out.println(m);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * 利用ResultMap进行结果映射
     * @throws Exception
     */
    @Test
    public void testSelectGoodsDTO() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            List<GoodsDTO> list = session.selectList("goods.selectGoodsDTO");
            for (GoodsDTO g : list) {
                System.out.println(g.getGoods().getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }


    /**
     * insert  插入数据
     * @throws Exception
     */
    @Test
    public void testInsertGoods() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = new Goods();
            goods.setTitle("测试商品");
            goods.setSubTitle("测试子标题");
            goods.setOriginalCost(200f);
            goods.setCurrentPrice(100f);
            goods.setDiscount(0.5f);
            goods.setIsFreeDelivery(1);
            goods.setCategoryId(43);
            //insert() 方法返回值代表本次成功插入的记录总数
            int num = session.insert("goods.insertGoods", goods);
            session.commit();  //提交事务数据
            System.out.println(goods.getGoodsId());
        } catch (Exception e) {
            if (session != null) {
                session.rollback();  //错误事务回滚数据
            }
            throw e;
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * update  更新数据
     * @throws Exception
     */
    @Test
    public void testUpdateGoods() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 2674);
            goods.setTitle("更新测试商品");
            int num = session.update("goods.updateGoods", goods);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw (e);
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }

    /**
     * delete语句
     * @throws Exception
     */
    @Test
    public void testDeleteGoods() throws Exception {
        SqlSession session = null;
        try {
            session = MyBatisUtils.openSession();
            int num = session.delete("goods.deleteGoods", 2674);
            session.commit();
        } catch (Exception e) {
            if (session != null) {
                session.rollback();
            }
            throw (e);
        } finally {
            MyBatisUtils.closeSession(session);
        }
    }
}






















