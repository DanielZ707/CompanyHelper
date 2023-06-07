import Popup from 'reactjs-popup';

const PopUp = () => {
    return (
        <Popup trigger={<button> Trigger</button>} position="right center">
            <div>Popup content here !!</div>
        </Popup>
    );
}

export default PopUp;