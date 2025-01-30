import React, { useState } from 'react';
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select";
import { Button } from "@/components/ui/button";

const QuotaSettings = () => {
    
    const [settings, setSettings] = useState({
        defaultQuota: '',
        maxQuotaPerStation: '',
        quotaResetPeriod: 'monthly'
    });

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setSettings(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log('Settings submitted:', settings);
    };

    return (
        <div className="space-y-6">
            <div>
                <h1 className="text-lg font-medium">Configure Quota Parameters</h1>
            </div>

            <div className="border rounded-lg p-4 max-w-sm">
                <form onSubmit={handleSubmit} className="space-y-8">
                    <div className="space-y-4">
                        <div className="grid w-full items-center gap-1.5">
                            <Label htmlFor="defaultQuota">Default Quota</Label>
                            <Input
                                id="defaultQuota"
                                name="defaultQuota"
                                type="number"
                                value={settings.defaultQuota}
                                onChange={handleInputChange}
                                min="0"
                                placeholder="Enter default quota"
                            />
                        </div>

                       
                    </div>

                    <div className="flex">
                        <Button type="submit">Save Settings</Button>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default QuotaSettings;