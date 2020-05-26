package com.lzhch.mybatisplus.generator;


import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @packageName： com.lzhch.mbatisplus.generator
 * @className: CodeGenerator
 * @description: MybatisPlus 3.3.0 GeneratorCode
 * @version: v1.0
 * @author: liuzhichao
 * @date: 2020-05-21 09:56
 */
public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        /**
         *  全局策略配置
         */
        GlobalConfig globalConfig = new GlobalConfig();
        // System.getProperty("user.dir") 获取项目的根目录
        String projectPath = System.getProperty("user.dir") + "/Springboot-MybatisPlus-Generator";
        // 生成文件的输出目录
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        // 开发人员
        globalConfig.setAuthor("liuzhichao");
        // 是否打开输出目录 默认为true
        globalConfig.setOpen(false);
        // 指定生成的主键的ID类型
        //globalConfig.setIdType(null);
        // 是否覆盖已有文件 默认为false
        //globalConfig.setFileOverride(false);
        // 开启 BaseResultMap 默认为false
        //globalConfig.setBaseResultMap(false);
        // 开启 baseColumnList 默认为false
        //globalConfig.setBaseColumnList(false);
        // 时间类型对应策略 默认为TIME_PACK
        //globalConfig.setDateType(DateType.TIME_PACK);
        // 是否在xml中添加二级缓存配置 默认为false
        //globalConfig.setEnableCache(false);
        // 开启 Kotlin 模式 默认为false
        //globalConfig.setKotlin(false);
        // 开启 swagger2 模式 默认为false
        //globalConfig.setSwagger2(false);
        // 开启 ActiveRecord 模式 默认为false
        //globalConfig.setActiveRecord(false);
        /**
         *  如下配置 %s 为占位符
         */
        // 实体命名方式 默认值：null 例如：%sEntity 生成 UserEntity
        //globalConfig.setEntityName(null);
        // mapper 命名方式   默认值：null 例如：%sDao 生成 UserDao
        //globalConfig.setMapperName(null);
        // Mapper xml 命名方式    默认值：null 例如：%sDao 生成 UserDao.xml
        //globalConfig.setXmlName(null);
        // service 命名方式     默认值：null 例如：%sBusiness 生成 UserBusiness
        //globalConfig.setServiceName(null);
        // service impl 命名方式    默认值：null 例如：%sBusinessImpl 生成 UserBusinessImpl
        //globalConfig.setServiceImplName(null);
        // controller 命名方式     默认值：null 例如：%sAction 生成 UserAction
        //globalConfig.setControllerName(null);

        mpg.setGlobalConfig(globalConfig);

        /**
         *  数据源配置 [通过该配置, 指定需要生成代码的具体数据库]
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl("jdbc:mysql://localhost:3306/myself_lzc?characterEncoding=UTF-8");
        dataSourceConfig.setDriverName("com.mysql.jdbc.Driver");
        dataSourceConfig.setUsername("root");
        dataSourceConfig.setPassword("root#123QAZ!");
        // 数据库类型 该类内置了常用的数据库类型【必须】
        //dataSourceConfig.setDbType(DbType.MYSQL);
        // 数据库信息查询类.默认由 dbType 类型决定选择对应数据库内置实现.实现 IDbQuery 接口自定义数据库查询 SQL 语句 定制化返回自己需要的内容
        //dataSourceConfig.setDbQuery();
        // 数据库 schema name.例如 PostgreSQL 可指定为 public
        //dataSourceConfig.setSchemaName();
        // 类型转换 默认由 dbType 类型决定选择对应数据库内置实现.
        //实现 ITypeConvert 接口自定义数据库 字段类型 转换为自己需要的 java 类型, 内置转换类型无法满足可实现 IColumnType 接口自定义
        //dataSourceConfig.setTypeConvert();

        mpg.setDataSource(dataSourceConfig);

        /**
         *  包名配置 [通过该配置,指定生成代码的包路径]
         */
        PackageConfig packageConfig = new PackageConfig();
        // 父包名。如果为空, 将下面子包名必须写全部, 否则就只需写子包名
        packageConfig.setParent("com.lzhch.mybatisplus.generator");
        // 父包模块名
        packageConfig.setModuleName(scanner("模块名(包名)"));
        // Entity包名
        //packageConfig.setEntity();
        // Service包名
        //packageConfig.setService();
        // Service Impl包名
        //packageConfig.setServiceImpl();
        // Mapper包名
        //packageConfig.setMapper();
        // Mapper XML包名
        //packageConfig.setXml();
        // Controller包名
        //packageConfig.setController();
        // 路径配置信息
        //packageConfig.setPathInfo();

        mpg.setPackageInfo(packageConfig);

        /**
         *  注入配置 [通过该配置,可注入自定义参数等操作以实现个性化操作]
         */
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            // 注入自定义 Map 对象(注意需要setMap放进去)
            @Override
            public void initMap() {
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出文件 [配置 FileOutConfig 指定模板文件、输出文件达到自定义文件生成目的]
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            // 生成 Entity 的 xml 文件
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /**
         *  自定义判断是否创建文件 实现 IFileCreate 接口
         *  该配置用于判断某个类是否需要覆盖创建, 当然你可以自己实现差异算法 merge 文件
         */
        /*
        injectionConfig.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录，自定义目录用");
                if (fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });
        */
        //自定义返回配置 Map 对象   该对象可以传递到模板引擎通过 cfg.xxx 引用
        //injectionConfig.setMap();
        injectionConfig.setFileOutConfigList(focList);
        mpg.setCfg(injectionConfig);

        /**
         *  模板配置 [可自定义代码生成的模板,实现个性化操作]
         */
        TemplateConfig templateConfig = new TemplateConfig();
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // Java 实体类模板
        // templateConfig.setEntity("templates/entity2.java");
        // Kotin 实体类模板
        //templateConfig.setEntityKt();
        // Service 类模板
        // templateConfig.setService();
        // Service impl 实现类模板
        //templateConfig.setServiceImpl();
        // mapper 模板
        //templateConfig.setMapper();
        // mapper xml 模板
        //templateConfig.setXml();
        // controller 控制器模板
        // templateConfig.setController();

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        /**
         *  数据库表配置 [通过该配置,可指定需要生成哪些表或者排除哪些表]
         */
        StrategyConfig strategy = new StrategyConfig();
        // 数据库表名映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 需要包含的表名, 当enableSqlFilter为false时, 允许正则表达式(与exclude二选一配置)
        strategy.setInclude(scanner("表名, 多个英文逗号分割").split(","));
        // 逻辑删除属性名称
        strategy.setLogicDeleteFieldName("is_delete");
        // 是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(true);
        // 是否生成实体时, 生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 是否大写命名
        //strategy.setCapitalMode(false);
        // 是否跳过视图
        //strategy.setSkipView(false);
        // 表前缀
        //strategy.setTablePrefix(packageConfig.getModuleName() + "_");
        // 字段前缀
        //strategy.setFieldPrefix("");
        // 自定义继承的Entity类全称, 带包名
        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
        // 自定义基础的Entity类, 写于父类中的公共字段
        //strategy.setSuperEntityColumns("id");
        // 自定义继承的Mapper类全称，带包名
        //strategy.setSuperMapperClass();
        // 自定义继承的Service类全称，带包名
        //strategy.setSuperServiceClass();
        // 自定义继承的ServiceImpl类全称，带包名
        //strategy.setSuperServiceImplClass();
        // 自定义继承的Controller类全称, 带包名
        //strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 自3.3.0起，模糊匹配表名（与notLikeTable二选一配置）
        //strategy.setLikeTable();
        // 自3.3.0起，模糊排除表名
        //strategy.setNotLikeTable();
        // 需要排除的表名，当enableSqlFilter为false时，允许正则表达式
        //strategy.setExclude();
        // 【实体】是否生成字段常量（默认 false）
        //strategy.setEntityColumnConstant(false);
        // Boolean类型字段是否移除is前缀（默认 false）
        //strategy.setEntityBooleanColumnRemoveIsPrefix(false);
        // 乐观锁属性名称
        //strategy.setVersionFieldName();
        // 表填充字段
        //strategy.setTableFillList();

        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();

    }

}
