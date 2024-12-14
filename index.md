# AI For Todo

一个智能化的待办事项管理应用，集成了定时提醒、智能分类和任务优先级推荐等 AI 功能。

## 功能特点

- 📝 智能任务管理
  - 自动识别任务类型
  - 智能优先级排序
  - 任务标签自动推荐

- ⏰ 智能提醒系统
  - 定时通知提醒
  - 智能震动提醒
  - 基于用户习惯的提醒时间推荐

- 🎯 任务追踪
  - 任务完成度统计
  - 进度可视化
  - 任务时间分析

- 🤖 AI 助手
  - 每日任务建议
  - 个性化完成每周任务规划
  - 任务规划

## 演示视频

### 手机端演示
[![手机端演示](https://img.youtube.com/vi/8ZyZ4g-Uoes/0.jpg)](https://youtu.be/8ZyZ4g-Uoes)

### 测试版本演示（修复bug后）
[![测试版本演示](https://img.youtube.com/vi/eYbQGtHEA2U/0.jpg)](https://youtu.be/eYbQGtHEA2U)

> 注意：手机端版本中存在一个已知的小bug，该问题在测试版本中已修复。建议使用最新的测试版本以获得更好的使用体验。

后面都是AI生成的，本人还没写，，后续有朋友需要配置可以补充

## 技术栈

- 💻 前端
  - Kotlin
  - Jetpack Compose
  - Material Design 3

- 🛠 后端
  - Room Database
  - Kotlin Coroutines
  - Flow

- 🧠 AI 功能
  - 机器学习模型集成
  - 自然语言处理
  - 用户行为分析

## 系统要求

### 客户端要求
- Android 14.0 (API 25) 或更高版本
- 最低 2GB RAM
- 500MB 可用存储空间

### AI 服务器要求
- Python 3.8+
- Docker (可选)
- 至少 4GB RAM
- GPU 支持 (推荐)

## 部署说明

### 1. AI 服务部署

#### 1.1 服务器配置
```bash
# 克隆 AI 服务仓库
git clone https://github.com/yourusername/ai-for-todo-server.git

# 安装依赖
pip install -r requirements.txt

# 配置环境变量
cp .env.example .env
# 编辑 .env 文件填写必要配置
```

#### 1.2 模型部署
```bash
# 下载预训练模型
python download_models.py

# 启动 AI 服务
python run_server.py
```

#### 1.3 Docker 部署（可选）
```bash
# 构建镜像
docker build -t ai-for-todo-server .

# 运行容器
docker run -d -p 8000:8000 ai-for-todo-server
```

### 2. 客户端配置

#### 2.1 基础配置
在 `app/src/main/res/values/config.xml` 中配置 AI 服务地址：

```xml
<resources>
    <string name="ai_server_url">http://your-server-address:8000</string>
    <string name="ai_api_key">your-api-key</string>
</resources>
```

#### 2.2 安装应用
1. 从 Release 页面下载最新版本的 APK
2. 在设备设置中允许安装未知来源的应用
3. 安装 APK 文件
4. 首次启动时授予必要权限

## 权限说明

- `SCHEDULE_EXACT_ALARM`: 用于设置精确的提醒时间
- `VIBRATE`: 用于震动提醒
- `INTERNET`: 用于同步数据和 AI 功能
- `RECEIVE_BOOT_COMPLETED`: 用于设备重启后恢复提醒

## 开发环境设置

1. 克隆仓库
```bash
git clone https://github.com/yourusername/ai-for-todo.git
```

2. 在 Android Studio 中打开项目

3. 同步 Gradle 文件

4. 运行项目

## AI 功能配置与故障排查

### 配置项
- 模型选择：可在 `.env` 文件中配置使用的模型类型
- API 限流设置：可配置每分钟最大请求次数
- 模型参数调整：根据实际需求调整模型参数

### 注意事项
1. AI 服务需要稳定的网络环境
2. 建议使用 HTTPS 进行安全传输
3. 需要定期更新模型以提升性能
4. 注意保护 API 密钥安全
5. 建议配置监控和告警机制

### 故障排查
如果 AI 功能无法正常使用，请检查：
1. AI 服务器是否正常运行
2. 网络连接是否正常
3. API 密钥是否正确
4. 客户端配置是否正确
5. 查看服务器日志获取详细错误信息

## 项目结构

```
app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/yourusername/aifortodo/
│   │   │       ├── data/
│   │   │       ├── di/
│   │   │       ├── domain/
│   │   │       ├── ui/
│   │   │       └── util/
│   │   └── res/
│   └── test/
├── build.gradle
└── proguard-rules.pro
```

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 版本历史

- 1.0.0
  - 初始版本发布
  - 基础任务管理功能
  - AI 辅助功能

## 作者

Your Name - [@yourusername](https://github.com/yourusername)

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 致谢

- [Android Jetpack](https://developer.android.com/jetpack)
- [Material Design](https://material.io/design)
- [Kotlin](https://kotlinlang.org/)
- [ToDometer](https://github.com/serbelga/ToDometer)