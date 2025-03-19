use iced::widget::text_editor;
use iced::Element;

fn main() -> iced::Result {
    iced::run("Multiline input", Links::update, Links::view)
}

#[derive(Default)]
struct Links {
    content: text_editor::Content,
}

#[derive(Debug, Clone)]
enum Message {
    Edit(text_editor::Action),
}

impl Links {
    fn update(&mut self, message: Message) {
        match message {
            Message::Edit(action) => {
                self.content.perform(action);
            }
        }
    }

    fn view(&self) -> Element<'_, Message> {
        text_editor(&self.content).on_action(Message::Edit).into()
    }
}
