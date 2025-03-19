use iced::widget::{button, column, row, text, text_editor, text_input};
use iced::{application, Element};
use std::fs;
use std::time::Duration;
use std::{io, thread};

//*****************************************************************************
// STRUCTS
//*****************************************************************************
#[derive(serde::Deserialize, serde::Serialize)]
#[serde(default)]
struct Data {
    url: String,
    path: String,

    #[serde(skip)]
    log_label: ButtonState,

    #[serde(skip)]
    content: text_editor::Content,
}

//*****************************************************************************
// ENUMS
//*****************************************************************************
#[derive(Debug, Clone)]
enum Message {
    MultiEdit(text_editor::Action),
    PathChanged(String),
    StartLog,
}

#[derive(serde::Deserialize, serde::Serialize)]
enum ButtonState {
    Invalid,
    Valid,
    Loading,
    Process,
    Error,
}

//*****************************************************************************
// IMPL
//*****************************************************************************
impl Default for Data {
    fn default() -> Self {
        Self {
            url: "http://example.com".to_owned(),
            path: "C:/Install/Browser.exe".to_owned(),

            log_label: ButtonState::Invalid,

            content: text_editor::Content::with_text("http://example.com"),
        }
    }
}

//*****************************************************************************
// MAIN
//*****************************************************************************
fn main() -> iced::Result {
    let app = application("ISP on Fire", Data::update, Data::view);
    iced::Application::run_with(app, || (Data::new(), iced::Task::none()))
}

impl Data {
    fn new() -> Self {
        if let Some(mut session) = load_serialized() {
            session.content = text_editor::Content::with_text(&session.url);
            // TOOD: possibly edit the buttonState here
            return session;
        }
        Default::default()
    }

    fn update(&mut self, message: Message) {
        match message {
            Message::MultiEdit(action) => {
                self.content.perform(action);
                if self.url != self.content.text() {
                    if valid_urls(&self.url) && valid_path(&self.path) {
                        self.log_label = ButtonState::Valid;
                    }
                }
                self.url = self.content.text()
            }
            Message::PathChanged(s) => {
                self.path = s;
            }
            Message::StartLog => {
                // call function
                self.log_label = ButtonState::Loading;
                simulate_sleep()
            }
            _ => {}
        }
        save_serialized(&self);
    }

    fn view(&self) -> Element<'_, Message> {
        let mut interface = column![
            row![
                text("Url(s): "),
                text_editor(&self.content).on_action(Message::MultiEdit),
            ],
            row![
                text("PATH: "),
                text_input(&self.path, &self.path).on_input(Message::PathChanged),
            ],
        ];

        let log_button;
        match self.log_label {
            ButtonState::Invalid => log_button = button("Start Logging"),
            ButtonState::Valid => log_button = button("Start Logging").on_press(Message::StartLog),
            ButtonState::Loading => log_button = button("Loading"),
            _ => log_button = button("TODO"),
        }

        interface = interface.push(log_button);
        interface.into()
    }
}

//*****************************************************************************
// UTIL
//*****************************************************************************
fn save_serialized(d: &Data) {
    let serialized = serde_json::to_string(d).expect("Failed to serialize data");
    fs::write("saved_data.json", serialized).expect("Unable to write file");
}

fn load_serialized() -> Option<Data> {
    if let Ok(data) = fs::read_to_string("saved_data.json") {
        if let Ok(saved_data) = serde_json::from_str(&data) {
            return Some(saved_data);
        }
    }
    None
}

fn valid_urls(text: &str) -> bool {
    if text.is_empty() {
        return false;
    }
    for line in text.lines() {
        // eprintln!("text: {}", line);
        if !line.contains("http://") {
            return false;
        }
    }
    true
}

//TODO: change this
fn valid_path(text: &str) -> bool {
    true
}

fn simulate_sleep() {
    thread::sleep(Duration::from_secs(5));
}
