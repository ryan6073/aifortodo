import json
from datetime import datetime

class ToolManager:
    def __init__(self, config_path="tools_config.json"):
        with open(config_path, 'r', encoding='utf-8') as f:
            config = json.load(f)
            self.toolList = config['toolList']
            self.policyList = config['policyList']

    def get_policy(self, name, data):
        """
        根据策略名称和数据生成提示词
        """
        return (self.policyList[name]["prompt"]
                .replace("{{time}}", datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
                .replace("{{data}}", data))

    def get_tool_list(self):
        """
        获取工具列表
        """
        return self.toolList

    def get_policy_list(self):
        """
        获取策略列表
        """
        return self.policyList


if __name__ == "__main__":
    tool_manager = ToolManager()
    r = tool_manager.get_policy("summary", "你好")
    print(r)
