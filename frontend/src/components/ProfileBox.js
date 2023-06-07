import {Avatar, Badge} from "@mui/material";
import Navbar from "./Navbar";


const ProfileBox = () => {
    return (
        <div className="profilePage">
            <Navbar/>
            <nav className="profile">
                <div className="profileBox">
                    <div className="avatarProfile">
                        <Badge
                            overlap="circular"
                            anchorOrigin={{vertical: 'bottom', horizontal: 'right'}}
                            badgeContent=" "
                            color="success"
                        >
                            <Avatar src={require('../images/avatar1.jpg')} sx={{width: 100, height: 100}}/>
                        </Badge>
                        <div className="profileFirst">
                            <div className="name">Cameron Brown</div>
                            <div className="function">CEO</div>
                        </div>
                    </div>
                    <div className="profileSecond">
                        Dedicated and driven construction manager with over 12 years of experience in the
                        construction management industry. Proven track record of successfully managing
                        multi-million dollar projects from conception to fruition. Effectively coordinate trades,
                        build partnerships and work collaboratively with architects, engineers, vendors, and
                        local officials to ensure the timely and budget-friendly completion of a project. Thorough
                        knowledge of building codes and regulations, and contract negotiation. Expertise in
                        team building, leadership and quality control management is integral to every job I take
                        on.
                    </div>
                    <div className="profileThird">
                        <div className="contact">
                            <div className="title">
                                Contact Details
                            </div>
                            <div className="skillRows">
                                <div className="skillsRow">
                                    Telephone: +91 123 456 7890
                                </div>
                                <div className="skillsRow">
                                    Email: abc@gmail.com
                                </div>
                                <div className="skillsRow">
                                    Address:
                                </div>
                                <ul>
                                    <li>Street: Racławicka 117/ 24</li>
                                    <li>Town: Kraków</li>
                                    <li>ZIP code: 39-591</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    );
}

export default ProfileBox;