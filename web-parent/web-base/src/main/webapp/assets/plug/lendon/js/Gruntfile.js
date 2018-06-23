
// 首先这个js要基于node.js


module.exports = function(grunt) {
    grunt.initConfig({
        //获取package.json信息
        pkg: grunt.file.readJSON('package.json'),
        // concat插件配置信息(合并)
        concat: {
            options: {
                // 定义一个用于插入合并输出文件之间的字符
                separator: '\n'
            },
            dist: {
                // 将要被合并的文件
                src: ['./*.js'],
                // 合并后的JS文件的存放位置
                dest: 'dist/<%= pkg.name %>Concat.js'
            }
        },
        // uglify插件的配置信息(压缩)
        uglify: {
            options: {
                // stripBanners: true,
                banner: '/*! <%=pkg.name%>-<%=pkg.version%>.js <%= grunt.template.today("yyyy-mm-dd HH:MM:ss") %> 北京轩慧国信科技有限公司*/\n'
            },
            dist: {
                files: [{
                    // 将要被压缩的文件
                    src: '<%=pkg.name%>.js',
                    // 压缩后的JS文件的存放位置
                    dest: '<%=pkg.name%>-<%=pkg.version%>.min.js'
                },{
                    'dist/<%= pkg.name %>Concat.min.js': ['<%= concat.dist.dest %>']
                }]
            }
        },
        // watch插件（实现自动化）
        watch: {
            dist: {
                files: ['./*.js','dist/*.js'],
                tasks: ['concat', 'uglify']
            }
        }
    });
    // 告诉grunt我们将使用的插件
    grunt.loadNpmTasks('grunt-contrib-concat');
    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-watch');
    //告诉grunt当我们在终端中输入grunt时需要做些什么（注意先后顺序）  
    grunt.registerTask('default', ['concat', 'uglify', 'watch']);
};