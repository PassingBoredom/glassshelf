import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.firefox.options import Options as Options_FF
from selenium.webdriver.firefox.service import Service 
from webdriver_manager.firefox import GeckoDriverManager
from beautifultable import BeautifulTable
import time

# Web driver init and details in global for convience 
SCROLL_PAUSE_TIME = 0.5
URL = "https://report.cs.rutgers.edu/nagiosnotes/iLab-machines.html"
opts = Options_FF()
opts.add_argument("-headless")
driver = webdriver.Firefox(service=Service(GeckoDriverManager().install()), options=opts)
driver.get(URL)

# Get scroll height
last_height = driver.execute_script("return document.documentElement.scrollHeight")
# inf loop to load everything on the page
while True:
    # Scroll down to bottom
    driver.execute_script("window.scrollTo(0, document.documentElement.scrollHeight);")
    # Wait to load page
    time.sleep(SCROLL_PAUSE_TIME)
    # Calculate new scroll height and compare with last scroll height
    new_height = driver.execute_script("return document.documentElement.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height

html = driver.page_source
soup = BeautifulSoup(html, "html.parser")
tables = soup.find_all("table")

def printILab():
    # from list of tables, iterate for the ones that I need
    for tab in tables[2:4:1]:
        if "units)" in tab.text:
            rows = tab.find_all("tr")
            cols = tab.find_all("th")

            section = getSection(rows[0].text)
            comps = getUnits(rows[0].text)
            
            # print("==================== iLab Status ====================")
            print(f'==================== {section}====================')
            
            status = BeautifulTable()
            status.set_style(BeautifulTable.STYLE_NONE)
            status.columns.header = ["Hostname", "GPU_Load", "Logins", "User_CPU", "System_CPU", "Misc"]
            for x in range(comps):
                makeTable(status, rows[x+2].text)

            print(status)

# Helpers for formatting
def getSection(str):
    title = str[0:str.find('('):]
    return title

def getUnits(str):
    num = str[str.find('(')+1::1]
    num = num[:num.find(' '):1]
    return int(num)

def beautifyPrint(text):
    separator = text.split()
    gpuNum = separator[0][separator[0].find('edu')+3::1] + '%'
    separator[0] = separator[0][0:separator[0].find('edu')+3:1]
    print(separator[0])
    print(gpuNum)
    separator[1] = separator[1][1::]
    print(separator[1])
    
def makeTable(table, txt):
    splitText = txt.split()
    gpuNum = splitText[0][splitText[0].find('edu')+3::1] + '%'
    splitText[0] = splitText[0][0:splitText[0].find('edu')+3:1]
    splitText[1] = splitText[1][1::]
    misc = splitText[4] + splitText[5]
    
    table.rows.append([splitText[0], gpuNum, splitText[1], splitText[2], splitText[3], misc])
    
if __name__ == "__main__":
    printILab()
    print("======================== END ========================")
    driver.close();