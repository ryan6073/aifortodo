import json
from datetime import datetime
from openai import OpenAI
from tools import ToolManager

tool_manager = ToolManager()
toolList = tool_manager.get_tool_list()
policyList = tool_manager.get_policy_list()


class ToolCallHandler:
    def __init__(self, config_path="config.json"):
        with open(config_path, 'r', encoding='utf-8') as f:
            self.config = json.load(f)
            
        self.client = OpenAI(
            base_url=self.config['openai']['base_url'],
            api_key=self.config['openai']['api_key']
        )
        self.model = self.client
        self.toolList = toolList
        self.policyList = policyList

    def get_policy(self, name, data):
        return (self.policyList[name]["prompt"]
                .replace("{{time}}", datetime.now().strftime("%Y-%m-%d %H:%M:%S"))
                .replace("{{data}}", data))

    def simulate_function_call(self, messages, data, tool=None):
        try:
            if tool is None:
                tool_selection_prompt = self.config['tool_selection']['prompt'].replace(
                    "{{tools}}", 
                    json.dumps(self.toolList, ensure_ascii=False, indent=2)
                )

                enhanced_messages = [
                    {"role": "system", "content": tool_selection_prompt},
                    *messages
                ]

                tool_selection_response = self.model.chat.completions.create(
                    model=self.config['model']['name'],
                    messages=enhanced_messages,
                    response_format={"type": "json_object"}
                )

                tool_selection = json.loads(tool_selection_response.choices[0].message.content)
            else:
                tool_selection = {
                    "tool_name": tool,
                    "reasoning": "force"
                }
                
            selected_tool = tool_selection['tool_name']
            tool_specific_prompt = self.get_policy(selected_tool, data)
            print(tool_specific_prompt)
            final_messages = [
                {"role": "system", "content": tool_specific_prompt},
                *messages
            ]

            final_response = self.model.chat.completions.create(
                model=self.config['model']['name'],
                messages=final_messages
            )

            return {
                "tool": selected_tool,
                "reasoning": tool_selection.get('reasoning', ''),
                "response": final_response.choices[0].message.content
            }

        except Exception as e:
            return {
                "tool": "default",
                "error": str(e),
                "response": "抱歉，处理您的请求时出现了错误。"
            }

    def process(self, messages, data, tool=None):
        return self.simulate_function_call(messages, data, tool)

def get_llm_response(query, data, tool=None):
    handler = ToolCallHandler()
    messages = [{"role": "user", "content": query}]
    while True:
        try:
            response = handler.process(messages, data, tool)
            print(response)
            if response['tool'] != 'summary' and response['tool'] != 'default':
                responseJSON = json.loads(response['response'])
                assert type(responseJSON) == dict
                assert type(responseJSON['content']) == str
                assert len(responseJSON['content']) > 0
            return response
        except Exception as e:
            print(f"Error: {e}")
            print("Retrying...")
            continue

if __name__ == "__main__":
    test_messages = [
        "帮我总结一下今天要做的事情",
        "提醒我下午3点开会",
        "添加一个买菜的任务"
    ]

    for msg in test_messages:
        data = """
{"query": "你好", "tasks": [{"description": "下周三，在逸夫楼14:00。", "name": "人工智能原理考试", "state": "进行中"}, {"description": "在12月15日提交项目演示。", "name": "提交项目", "state": "进行中"}]}
       """.strip()
        result = get_llm_response(msg, data)
        print(f"输入: {msg}")
        print(f"工具: {result['tool']}")
        print(f"推理: {result.get('reasoning', '')}")
        print(f"响应: {result['response']}")
        print("-" * 50)
