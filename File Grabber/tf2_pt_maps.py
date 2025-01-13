import requests
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver import ActionChains
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.actions.action_builder import ActionBuilder
from selenium.webdriver.common.actions.mouse_button import MouseButton
from selenium.webdriver.common.by import By
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service as ChromeService
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.firefox.options import Options as Options_FF
from webdriver_manager.firefox import GeckoDriverManager
from selenium.webdriver.firefox.service import Service 
import time
import os
import sys

# Parent site: https://passtime.tf/maps
# active rotation 2025/1/12
maps = [
    ("pass_arena2", "https://passtime.tf/maps/pass_arena2"),
    ("pass_boutique", "https://passtime.tf/maps/pass_boutique"),
    ("pass_maple", "https://passtime.tf/maps/pass_maple"),
    ("pass_plexiglass", "https://passtime.tf/maps/pass_plexiglass"),
    ("pass_ruin", "https://passtime.tf/maps/pass_ruin"),
    ("pass_stadium", "https://passtime.tf/maps/pass_stadium"),
    ("pass_stonework", "https://passtime.tf/maps/pass_stonework"),
    ("pass_torii", "https://passtime.tf/maps/pass_torii")
]

download_path = ""
firefox_profile_path = ""

def custom_settings():
    f = open("pt_settings.out", "r")
    settings = f.readlines()
    
    # this is a really bad way to set a global setting 
    # would be better to create a map of some sort
    # also doesnt check for a bad settings file
    global download_path
    global firefox_profile_path
    download_path = settings[0].rstrip('\n')
    firefox_profile_path = settings[1].rstrip('\n')

def getDir():
    if download_path == "":
        print("Failed to build 'download_path' from config")
        sys.exit()
    # has proper path, get map list while cleaning names
    dir_list = os.listdir(download_path)
    return dir_list

def poll_map(name):
    list = getDir()
    if any(name in item for item in list): 
        return
    time.sleep(5)
    poll_map(name)

def download_map(name, link):
    if firefox_profile_path == "":
        print("Failed to build 'firefox_profile_path' from config")
        sys.exit()
    # has a profile, start driver
    profile = webdriver.FirefoxProfile(firefox_profile_path)
    opts = Options_FF()
    opts.add_argument("-headless")
    opts.profile = profile
    driver = webdriver.Firefox(service=Service(GeckoDriverManager().install()), options=opts)
    driver.get(link)
    
    try: 
        # wait for page to load
        element = WebDriverWait(driver, 15).until(
            EC.element_to_be_clickable((By.CLASS_NAME, "js-download"))
    )
    except:
        print("Taking too long, do it manually")
        sys.exit()
    else: 
        # click download button
        clickable = driver.find_element(By.CLASS_NAME, "js-default-download")
        ActionChains(driver).click(clickable).perform()
        # wait for file
        poll_map(name)
    finally:
        # close driver
        driver.close()
    # should look into having 1 driver at all times then replacing the link 
    # to reduce possible overhead in opening / closing webdriver

if __name__ == "__main__": 
    # to avoid saving local links into repo, read from some file
    custom_settings()
    
    # download the last active maps 
    print("Downloading Active Map Rotation since: 2025/1/12")
    for map in maps:
        # page is static, dont need driver here
        page = requests.get(map[1])
        soup = BeautifulSoup(page.content, "html.parser")
        links = soup.find_all('a')
        
        # instead of js interaction, assume lastest map is "top" link
        # 'href' to remove html tag
        # skip 1,2 links for button link and parent link
        download_map(map[0], links[2]["href"])
        print("Downloaded map: " + map[0] + " from: " + links[2]["href"])
    print("Finished Downloading")
    
    # Add option later for manging map files themselves into proper dir
    # Add cleanup for folder as I'm doing partial matches for map name
    # Add threading, there is no need to sequentially download the files, get them all at once
    # its impossible to know the latest map name anyway so old files must be removed
    # types involve raw(bsp) and compressed(bz2), maybe a function to auto decompress?
    
    