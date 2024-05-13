import tkinter as tk
from tkinter import filedialog
import cv2
from PIL import Image, ImageTk
from img_split import get1
from identify import get2
from rabbitmq_publisher import load


# 提前存好图片名信息
class img_msg:
    def __init__(self, img_url, car_license):
        self.img_url = img_url
        self.car_license = car_license

def open_image():
    # 打开文件对话框选择图片
    file_path = filedialog.askopenfilename(filetypes=[("Image files", "*.png;*.jpg;*.jpeg;*.gif;*.bmp")])
    img_msg.img_url = file_path
    # 使用OpenCV读取图片
    image = cv2.imread(file_path)
    # OpenCV默认读取的是BGR格式，转换为RGB以便正确显示
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    img = image
    if file_path:
        # 将OpenCV图像转换为PIL图像，再转换为Tkinter可以显示的格式
        image_pil = Image.fromarray(image)
        photo = ImageTk.PhotoImage(image_pil)

        # 更新标签以显示新图片
        label_image.config(image=photo)
        # 保持对photo的引用，防止被垃圾回收
        label_image.image = photo
        get1(img_msg.img_url)

def idf_img():
    # 打开文件对话框选择图片
    img_url = img_msg.img_url
    file_path = img_url.split('.')[0] + '-split.jpg'
    s = get2(file_path)

    if s is not None and s != "":
        load(s)
        print("发送成功")
    else:
        print("发送失败")

    label.config(text=s)

    # 使用OpenCV读取图片
    image = cv2.imread(file_path)
    # OpenCV默认读取的是BGR格式，转换为RGB以便正确显示
    image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)

    if file_path:
        # 将OpenCV图像转换为PIL图像，再转换为Tkinter可以显示的格式
        image_pil = Image.fromarray(image)
        photo = ImageTk.PhotoImage(image_pil)

        # 更新标签以显示新图片
        label_image2.config(image=photo)
        # 保持对photo的引用，防止被垃圾回收
        label_image2.image = photo



root = tk.Tk()
root.title("车牌识别")

# 创建左边部分
frame_left = tk.Frame(root)
frame_left.pack(side=tk.LEFT, fill=tk.BOTH, expand=True, padx=10, pady=10)

# 用于显示图片的标签
label_image = tk.Label(frame_left)
label_image.pack(expand=True, padx=10, pady=10)

# 上传和识别按钮
button_upload = tk.Button(frame_left, text="上传", command=open_image)
button_upload.pack(padx=10, pady=5)

button_sb = tk.Button(frame_left, text="识别", command=idf_img)
button_sb.pack(padx=10, pady=5)

# 假设识别按钮的功能已经整合到上传按钮中（这里不需要额外的识别按钮）

# 创建右边部分
frame_right = tk.Frame(root)
frame_right.pack(side=tk.RIGHT, fill=tk.BOTH, expand=True, padx=10, pady=10)

# 用于显示图片的标签
label_image2 = tk.Label(frame_right)
label_image2.pack(expand=True, padx=10, pady=10)

# 用于显示识别后的车牌文本的输入框（这里不显示图片，仅作为示例）
# entry_plate_text = tk.Entry(frame_right, width=50)
# entry_plate_text.pack(side=tk.BOTTOM, fill=tk.X, expand=True, padx=10, pady=10)

# 创建一个Label部件来显示字符串
str1 = ''
label = tk.Label(frame_right, text=str1)
label.pack(side=tk.BOTTOM, fill=tk.X, expand=True, padx=10, pady=10)

# （可选）你可以在这里添加用于显示识别后车牌图片的标签
# 但由于我们模拟中不返回图片，这里就不添加了

root.mainloop()

