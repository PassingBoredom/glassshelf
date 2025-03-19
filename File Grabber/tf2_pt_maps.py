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

def custom_settings():
    f = open("pt_settings.out", "r")
    settings = f.readlines()

    # this is a really bad way to set a global setting 
    # would be better to create a map of some sort
    # also doesnt check for a bad settings file
    global download_path
    download_path = settings[0].rstrip('\n')

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

def fastdl_map(name, link):
    response = requests.get(link)
    file = download_path + "\\" + name
    with open(file, 'wb') as file:
        file.write(response.content)

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

        # get map name from ddl 
        link_split = links[2]["href"].split("/")
        map_name = link_split[len(link_split) - 1]
        
        # instead of js interaction, assume lastest map is "top" link
        # 'href' to remove html tag
        # skip 1,2 links for button link and parent link
        fastdl_map(map_name, links[2]["href"])
        print("Downloaded \n\tmap: " + map[0] + "\n\tfrom: " + links[2]["href"])
    print("Finished Downloading")

    # Add option later for manging map files themselves into proper dir
    # Add threading, there is no need to sequentially download the files, get them all at once

