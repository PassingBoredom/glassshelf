use iced::widget::text_input;
use iced::Element;

fn main() -> iced::Result {
    iced::run("Text Input", TextBox::update, TextBox::view)
}

#[derive(Default)]
struct TextBox {
    text: String,
}

#[derive(Debug, Clone)]
enum Message {
    InputChanged(String),
}

impl TextBox {
    fn update(&mut self, message: Message) {
        match message {
            Message::InputChanged(s) => {
                self.text = s;
            }
        }
    }
    fn view(&self) -> Element<'_, Message> {
        text_input("Filler Text", &self.text)
            .on_input(Message::InputChanged)
            .into()
    }
}
