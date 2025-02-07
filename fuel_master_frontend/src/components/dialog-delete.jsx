import { Trash2 } from "lucide-react"
import { Dialog, DialogContent, DialogDescription, DialogFooter, DialogHeader, DialogTitle, DialogTrigger } from "./ui/dialog"
import { Button } from "./ui/button"
import PropTypes from 'prop-types'
import { useState } from "react"
const DialogDelete = ({ onDelete }) => {
    const [open, setOpen] = useState(false);
    const handleDelete = () => {
        onDelete();
        setOpen(false);
    }
    return (
        <Dialog open={open} onOpenChange={setOpen}>
            <DialogTrigger variant="ghost" size="icon">
                <Button variant="ghost" size="icon">
                    <Trash2 className="h-4 w-4" />
                </Button>
            </DialogTrigger>
            <DialogContent>
                <DialogHeader>
                    <DialogTitle>Are you absolutely sure?</DialogTitle>
                    <DialogDescription>
                        This action cannot be undone. Are you sure you want to permanently
                        delete this record?
                    </DialogDescription>
                </DialogHeader>
                <DialogFooter>
                    <Button type="submit" onClick={handleDelete}>Confirm</Button>
                </DialogFooter>
            </DialogContent>
        </Dialog>

    )
}

DialogDelete.propTypes = {
    onDelete: PropTypes.func.isRequired,
}

export default DialogDelete