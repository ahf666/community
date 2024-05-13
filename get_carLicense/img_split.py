import cv2
from matplotlib import pyplot as plt

def get1(img_url):

    # plt显示彩色图片
    def plt_show0(img):
        # cv2与plt的图像通道不同：cv2为[b,g,r];plt为[r, g, b]
        b, g, r = cv2.split(img)
        img = cv2.merge([r, g, b])
        plt.imshow(img)
        plt.show()


    # plt显示灰度图片
    def plt_show(img):
        plt.imshow(img, cmap='gray')
        plt.show()


    # 图像去噪灰度处理
    def gray_guss(image):
        image = cv2.GaussianBlur(image, (3, 3), 0)
        gray_image = cv2.cvtColor(image, cv2.COLOR_RGB2GRAY)
        return gray_image


    # 读取待检测图片
    origin_image = cv2.imread(img_url)
    # 复制一张图片，在复制图上进行图像操作，保留原图
    image = origin_image.copy()
    # 图像去噪灰度处理
    gray_image = gray_guss(image)
    # x方向上的边缘检测（增强边缘信息）
    Sobel_x = cv2.Sobel(gray_image, cv2.CV_16S, 1, 0)
    absX = cv2.convertScaleAbs(Sobel_x)
    image = absX

    # 图像阈值化操作——获得二值化图
    ret, image = cv2.threshold(image, 0, 255, cv2.THRESH_OTSU)
    # 显示灰度图像
    # plt_show(image)
    # 形态学（从图像中提取对表达和描绘区域形状有意义的图像分量）——闭操作
    kernelX = cv2.getStructuringElement(cv2.MORPH_RECT, (30, 10))
    image = cv2.morphologyEx(image, cv2.MORPH_CLOSE, kernelX, iterations=1)
    # 显示灰度图像
    # plt_show(image)

    # 腐蚀（erode）和膨胀（dilate）
    kernelX = cv2.getStructuringElement(cv2.MORPH_RECT, (50, 1))
    kernelY = cv2.getStructuringElement(cv2.MORPH_RECT, (1, 20))
    # x方向进行闭操作（抑制暗细节）
    image = cv2.dilate(image, kernelX)
    image = cv2.erode(image, kernelX)
    # y方向的开操作
    image = cv2.erode(image, kernelY)
    image = cv2.dilate(image, kernelY)
    # 中值滤波（去噪）
    image = cv2.medianBlur(image, 21)
    # 显示灰度图像
    plt_show(image)
    # 获得轮廓
    contours, hierarchy = cv2.findContours(image, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)

    for item in contours:
        rect = cv2.boundingRect(item)
        x = rect[0]
        y = rect[1]
        weight = rect[2]
        height = rect[3]
        # 根据轮廓的形状特点，确定车牌的轮廓位置并截取图像
        if (weight > (height * 2.8)) and (weight < (height * 4.8)) and weight*height > 1000:
            image = origin_image[y:y + height, x:x + weight]
            plt_show0(image)
            # 图像去噪灰度处理
            gray_image = gray_guss(image)

            name = img_url.split('.')[0] + '-split.jpg'
            cv2.imwrite(name, gray_image)




